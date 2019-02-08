# TABLE for saving user information
CREATE TABLE USER (
	id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	username VARCHAR(100) NOT NULL,
	firstname VARCHAR(80) NOT NULL,
	lastname VARCHAR(80) NOT NULL,
	PASSWORD VARCHAR(128) NOT NULL,
	salt VARCHAR(20) NOT NULL,
	admin BOOLEAN NOT NULL,
	country VARCHAR(20) NOT NULL,
	state VARCHAR(20) NOT NULL,
	zip VARCHAR(20) NOT NULL,
	activated INT(1) NOT NULL,
	registerTime BIGINT NOT NULL,
	lastLoginTime BIGINT NOT NULL,
	lastModifiedTime BIGINT NOT NULL,
	email varchar(200) NOT NULL
);

# Main table for vehicle
CREATE TABLE vehicle(
	id BIGINT NOT NULL PRIMARY KEY,
	VIN VARCHAR(20) NOT NULL,
	make VARCHAR(100) NOT NULL,
	model VARCHAR(100) NOT NULL,
	price double NOT NULL,
	STATUS TINYINT(2) NOT NULL,
	transmission TINYINT NOT NULL,
	YEAR INT(10) NOT NULL,
	process TINYINT NOT NULL,
	createTime BIGINT NOT NULL,
	lastModifyTime BIGINT NOT NULL,
	creatorId BIGINT NOT NULL,
	lastModifierId BIGINT NOT NULL
);

# Vehicle detail
CREATE TABLE vehicle_detail(
	id BIGINT NOT NULL PRIMARY KEY,
	zip VARCHAR(20),
	TRIM TINYINT,
	bodytype TINYINT,
	ExteriorColour TINYINT,
	mileage DOUBLE,
	drivetrain TINYINT,
	fueltype TINYINT,
	ENGINE TINYINT,
	doors TINYINT,
	seatNum TINYINT
);

# Message table
CREATE TABLE message(
	id BIGINT NOT NULL PRIMARY KEY,
	fromUser BIGINT NOT NULL,
	toUser BIGINT NOT NULL,
	title TEXT NOT NULL,
	msg TEXT NOT NULL,
	hasRead BOOLEAN NOT NULL,
	sendTime BIGINT NOT NULL,
	senderName varchar(100) NOT NULL
);