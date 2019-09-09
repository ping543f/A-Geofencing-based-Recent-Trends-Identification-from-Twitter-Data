# A Geofencing-based Recent Trends Identification from Twitter Data

The paper has two implementation.
1. Initial one is implemented in Java which has a GUI for all the operations like data load and visualaization. This
implementation needs mysql database and you need to import data to mysql table manually afetr converting the collected
twitter data to csv.

Table structure is as follows:

TABLE `status` (
  `srial` int(16) NOT NULL,
  `URL` varchar(200) NOT NULL,
  `Keywords` varchar(200) NOT NULL,
  `KeywordCount` varchar(200) NOT NULL,
  `DateTime` varchar(30) NOT NULL,
  `FavoriteCount` varchar(200) NOT NULL,
  `Retweet` varchar(200) NOT NULL,
  `Lang` varchar(200) NOT NULL,
  `LinkCount` varchar(200) NOT NULL,
  `Link1` varchar(200) NOT NULL,
  `Link2` varchar(200) NOT NULL,
  `Link3` varchar(200) NOT NULL,
  `Author` varchar(200) NOT NULL,
  `Text` varchar(260) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `Followers` varchar(200) NOT NULL,
  `Friends` varchar(200) NOT NULL,
  `Location` varchar(200) NOT NULL,
  `Timezone` varchar(200) NOT NULL,
  `UTCOffset` varchar(200) NOT NULL,
  `hashtags` varchar(200) NOT NULL,
  `H1` varchar(200) NOT NULL
)

1.1 For modifying anything in java based implementation the source codes with all used libraries are provided in the 
GUI-in-JAVA-compiled-app folder. After modifying it you need to recompile the app.

1.2 You need to install mysql and creat the table as mentioned earlier.

1.3 For importing fields to mysql table from csv file please use this headings/fieldnames
"URL,Keywords,KeywordCount,DateTime,FavoriteCount,Retweet,Lang,LinkCount,Link1,Link2,Link3,Author,Text,Followers,Friends,Location,Timezone,UTCOffset,hashtags"

2. Final implementation is done using python. This one has no gui only CLI output and it does not need any databse. just need to mentiuon the
csv file and it will do the rest. twitter-implementation-python.py file contains it all.

3. Twitter data collection part has been used from Hale, S. A. (2014) Global Connectivity and Multilinguals in the Twitter Network. 
   In Proceedings of the 2014 ACM Annual Conference on Human Factors in Computing Systems, 
   ACM (Montreal, Canada). Detail instruction is provided inside the CLI-data-collector-python folder.

4. The latest experiment is done by using 1hourdata.csv data.
 
Please cite our work if you use this in any academic or industrial project and publications.
