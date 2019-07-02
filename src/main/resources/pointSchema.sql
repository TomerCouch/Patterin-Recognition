create table lineSegment(id int identity primary key, m double, n double);
create table point(id int identity primary key, x int, y int);
create table linePoint(pointID int, lineID int,
                        primary key (pointID, lineID),
                        constraint pointFK foreign key (pointID) references point(id),
                        constraint lineFK foreign key (lineID) references lineSegment(id))
