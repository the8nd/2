package vsu_archive.dao;

public interface DaoFactory<Context> {
    interface DaoCreator<Context> {
        @SuppressWarnings("rawtypes")
        GenericDao create(Context context);
    }
    Context getContext();
    <T extends MyObject> GenericDao<T> getDao(Context context, Class<T> dtoClass);
}
