package vsu_archive;

import vsu_archive.domain.ArchiveFile;
import vsu_archive.domain.File;
import vsu_archive.domain.Archive;
import vsu_archive.mysql.MySqlDaoFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static final MySqlDaoFactory factory = MySqlDaoFactory.instance;

    private static String readData(String name) {
        System.out.printf("%s: ", name);
        return new Scanner(System.in).nextLine();
    }
    private static Date getDate() {
        try {
            var string = readData("Введите дату");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d M yyyy");
            LocalDate date = LocalDate.parse(string, formatter);
            return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception ex) {
            System.out.println("Неверный формат. Правильный: 25 12 2020");
            return null;
        }
    }

    private static void selectArchiveFiles() {
        for (ArchiveFile archive_file : factory.getDao(factory.getContext(), ArchiveFile.class).selectAll()) {
            System.out.printf(
                    "id=%d|archive_id=%d|file_id=%d\n",
                    archive_file.getId(),
                    archive_file.getArchive().getId(),
                    archive_file.getFile().getId()
            );
        }
    }
    private static void selectFiles() {
        for (File file : factory.getDao(factory.getContext(), File.class).selectAll()) {
            System.out.printf("id=%d|file_name=%s\n", file.getId(), file.getFileName());
        }
    }
    private static void selectArchives() {
        for (Archive archive : factory.getDao(factory.getContext(), Archive.class).selectAll()) {
            System.out.printf("id=%d|title=%s|creation_time=%s\n",
                    archive.getId(),
                    archive.getTitle(),
                    archive.getCreationTime().toString()
            );
        }
    }
    private static void insertArchiveFiles() {
        var archive_file = new ArchiveFile();
        var archive_id = Integer.parseInt(readData("Введите id архива"));
        var archive = factory.getDao(factory.getContext(), Archive.class).getObjectById(archive_id);
        if (archive == null) {
            System.out.println("Неизвестный архив");
            return;
        }
        archive_file.setArchive(archive);
        var file_id = Integer.parseInt(readData("Введите id файла"));
        var file = factory.getDao(factory.getContext(), File.class).getObjectById(file_id);
        if (file == null) {
            System.out.println("Неизвестный файл");
            return;
        }
        archive_file.setFile(file);
        factory.getDao(factory.getContext(), ArchiveFile.class).insert(archive_file);
    }
    private static void insertFiles() {
        var file = new File();
        file.setFileName(readData("Введите название файла"));
        factory.getDao(factory.getContext(), File.class).insert(file);
    }
    private static void insertArchives() {
        var archive = new Archive();
        archive.setTitle(readData("Введите название архива"));
        var date = getDate();
        if (date == null) {
            return;
        }
        archive.setCreationTime(date);
        factory.getDao(factory.getContext(), Archive.class).insert(archive);
    }
    private static void updateArchiveFiles() {
        var archive_file = factory.getDao(factory.getContext(), ArchiveFile.class).getObjectById(Integer.parseInt(readData("Введите id изменяемого объекта")));
        if (archive_file == null) {
            System.out.println("Неизвестный архивный файл");
            return;
        }
        var archive_id = Integer.parseInt(readData("Введите id архива"));
        var archive = factory.getDao(factory.getContext(), Archive.class).getObjectById(archive_id);
        if (archive == null) {
            System.out.println("Неизвестный архив");
            return;
        }
        archive_file.setArchive(archive);
        var file_id = Integer.parseInt(readData("Введите id файла"));
        var file = factory.getDao(factory.getContext(), File.class).getObjectById(file_id);
        if (file == null) {
            System.out.println("Неизвестный файл");
            return;
        }
        archive_file.setFile(file);
        factory.getDao(factory.getContext(), ArchiveFile.class).update(archive_file);
    }
    private static void updateFiles() {
        var file = factory.getDao(factory.getContext(), File.class).getObjectById(Integer.parseInt(readData("Введите id изменяемого объекта")));
        if (file == null) {
            System.out.println("Неизвестный файл");
            return;
        }
        file.setFileName(readData("Введите название файла"));
        factory.getDao(factory.getContext(), File.class).update(file);
    }
    private static void updateArchives() {
        var archive = factory.getDao(factory.getContext(), Archive.class).getObjectById(Integer.parseInt(readData("Введите id изменяемого объекта")));
        if (archive == null) {
            System.out.println("Неизвестный архив");
            return;
        }
        archive.setTitle(readData("Введите название архива"));
        var date = getDate();
        if (date == null) {
            return;
        }
        archive.setCreationTime(date);
        factory.getDao(factory.getContext(), Archive.class).update(archive);
    }
    private static void deleteArchiveFiles() {
        var archive_file = factory.getDao(factory.getContext(), ArchiveFile.class).getObjectById(Integer.parseInt(readData("Введите id удаляемого объекта")));
        if (archive_file == null) {
            System.out.println("Неизвестный архивный файл");
            return;
        }
        factory.getDao(factory.getContext(), ArchiveFile.class).delete(archive_file);
    }
    private static void deleteFiles() {
        var file = factory.getDao(factory.getContext(), File.class).getObjectById(Integer.parseInt(readData("Введите id удаляемого объекта")));
        if (file == null) {
            System.out.println("Неизвестный файл");
            return;
        }
        factory.getDao(factory.getContext(), File.class).delete(file);
    }
    private static void deleteArchives() {
        var archive = factory.getDao(factory.getContext(), Archive.class).getObjectById(Integer.parseInt(readData("Введите id удаляемого объекта")));
        if (archive == null) {
            System.out.println("Неизвестная архив");
            return;
        }
        factory.getDao(factory.getContext(), Archive.class).delete(archive);
    }

    public static HashMap<Integer, String> helpers = new HashMap<>() {{
        put(1, "1 = просмотреть архивные файлы");
        put(2, "2 = просмотреть файлы");
        put(3, "3 = просмотреть архивы");
        put(4, "4 = вставть архивные файлы");
        put(5, "5 = вставть файлы");
        put(6, "6 = вставть архивы");
        put(7, "7 = обновить архивные файлы");
        put(8, "8 = обновить файлы");
        put(9, "9 = обновить архивы");
        put(10, "10 = удалить архивные файлы");
        put(11, "11 = удалить файлы");
        put(12, "12 = удалить архивы");
    }};
    public static HashMap<Integer, Runnable> actions = new HashMap<>() {{
        put(1, Main::selectArchiveFiles);
        put(2, Main::selectFiles);
        put(3, Main::selectArchives);
        put(4, Main::insertArchiveFiles);
        put(5, Main::insertFiles);
        put(6, Main::insertArchives);
        put(7, Main::updateArchiveFiles);
        put(8, Main::updateFiles);
        put(9, Main::updateArchives);
        put(10, Main::deleteArchiveFiles);
        put(11, Main::deleteFiles);
        put(12, Main::deleteArchives);
    }};

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        helpers.values().forEach(System.out::println);
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                var a = scanner.nextInt();
                actions.entrySet().stream().filter(x -> x.getKey()==a).findFirst().ifPresentOrElse(
                        x -> x.getValue().run(),
                        () -> System.out.println("Неизвестное действие")
                );
            } catch (Exception ex) {
                System.out.println("Неизвестное действие");
            }
            helpers.values().forEach(System.out::println);
        }
    }
}
