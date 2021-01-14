SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `archive`
--

CREATE TABLE IF NOT EXISTS `archive` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(65) COLLATE utf8_unicode_ci NOT NULL,
  `creation_time` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- Dumping data for table `archive`
--

INSERT INTO `archive` (`id`, `title`, `creation_time`) VALUES
(1, 'first.zip', '2021-01-14'),
(2, 'second.zip', '2021-01-15');

-- --------------------------------------------------------

--
-- Table structure for table `file`
--

CREATE TABLE IF NOT EXISTS `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fileName` varchar(65) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- Dumping data for table `file`
--

INSERT INTO `file` (`id`, `fileName`) VALUES
(1, 'test.exe'),
(2, 'test.pdf'),
(3, 'test.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `archive_file`
--

CREATE TABLE IF NOT EXISTS `archive_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `archive_id` int(11) NOT NULL,
  `file_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1` (`archive_id`),
  KEY `fk2` (`file_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `archive`
--

INSERT INTO `archive_file` (`id`, `archive_id`, `file_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 3);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `archive`
--
ALTER TABLE `archive_file`
  ADD CONSTRAINT `archive_ibfk_1` FOREIGN KEY (`archive_id`) REFERENCES `archive` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `archive_file`
  ADD CONSTRAINT `archive_ibfk_2` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;