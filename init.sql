SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE IF NOT EXISTS `users` (
  `id` int UNSIGNED NOT NULL,
  `login` varchar(25) NOT NULL DEFAULT '',
  `password` varchar(10) NOT NULL DEFAULT '',
  `lastName` varchar(30) NOT NULL DEFAULT '',
  `firstName` varchar(30) NOT NULL DEFAULT '',
  `email` varchar(30) DEFAULT NULL,
  `role` varchar(10) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `users`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;
