CREATE TABLE `users` IF NOT EXISTS (
  `id` int(10) unsigned NOT NULL auto_increment,
  `login` varchar(25) NOT NULL default '',
  `password` varchar(10) NOT NULL default '',
  `nom` varchar(30) NOT NULL default '',
  `prenom` varchar(30) NOT NULL default '',
  `email` varchar(30) default NULL,
  `role` varchar(10) NOT NULL default '',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;