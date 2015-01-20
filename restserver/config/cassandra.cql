CREATE KEYSPACE showcase WITH REPLICATION =
{ 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3 };

 drop table depositorder;
 
 create table depositorder (
 id timeuuid ,
 userid bigint,
 username varchar,
 amount decimal,
 status int,
 year int,
 month int,
 day int,
 hour int,
 min int,
 createtime timestamp,
 primary key ((userid,status,year,month),day,hour,min,id)) WITH CLUSTERING ORDER BY ( day DESC ,hour DESC ,min DESC, id DESC);