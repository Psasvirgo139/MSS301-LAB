IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'MSS301_2026_PE')
BEGIN
    CREATE DATABASE [MSS301_2026_PE];
END
GO

USE [MSS301_2026_PE]
GO

IF OBJECT_ID('dbo.reservations', 'U') IS NOT NULL
    DROP TABLE [dbo].[reservations];
GO

IF OBJECT_ID('dbo.rooms', 'U') IS NOT NULL
    DROP TABLE [dbo].[rooms];
GO

CREATE TABLE [dbo].[rooms] (
    [room_id]         INT              IDENTITY(1,1) NOT NULL,
    [room_number]     NVARCHAR(20)     NOT NULL,
    [room_type]       NVARCHAR(10)     NOT NULL,
    [price_per_night] DECIMAL(18,2)    NOT NULL,
    [capacity]        INT              NOT NULL,
    [floor]           INT              NOT NULL,
    [status]          NVARCHAR(15)     NOT NULL,

    CONSTRAINT [PK_Rooms] PRIMARY KEY CLUSTERED ([room_id] ASC),
    CONSTRAINT [UQ_Rooms_Number] UNIQUE NONCLUSTERED ([room_number] ASC),
    CONSTRAINT [CK_Rooms_Type] CHECK ([room_type] IN ('SINGLE', 'DOUBLE', 'SUITE', 'DELUXE')),
    CONSTRAINT [CK_Rooms_Price] CHECK ([price_per_night] > 0),
    CONSTRAINT [CK_Rooms_Capacity] CHECK ([capacity] >= 1 AND [capacity] <= 10),
    CONSTRAINT [CK_Rooms_Floor] CHECK ([floor] >= 1),
    CONSTRAINT [CK_Rooms_Status] CHECK ([status] IN ('AVAILABLE', 'OCCUPIED', 'MAINTENANCE'))
);
GO

CREATE TABLE [dbo].[reservations](
    [reservation_id]   INT             IDENTITY(1,1) NOT NULL,
    [guest_name]       NVARCHAR(100)   NOT NULL,
    [guest_email]      NVARCHAR(100)   NOT NULL,
    [guest_phone]      NVARCHAR(20)    NOT NULL,
    [check_in_date]    DATE            NOT NULL,
    [check_out_date]   DATE            NOT NULL,
    [number_of_guests] INT             NOT NULL,
    [total_amount]     DECIMAL(18,2)   NOT NULL,
    [status]           NVARCHAR(15)    NOT NULL,
    [room_id]          INT             NOT NULL,

    CONSTRAINT [PK_Reservations] PRIMARY KEY CLUSTERED ([reservation_id] ASC),
    CONSTRAINT [CK_Reservations_Dates] CHECK ([check_out_date] > [check_in_date]),
    CONSTRAINT [CK_Reservations_Guests] CHECK ([number_of_guests] >= 1),
    CONSTRAINT [CK_Reservations_Amount] CHECK ([total_amount] >= 0),
    CONSTRAINT [CK_Reservations_Status] CHECK ([status] IN ('CONFIRMED', 'CHECKED_IN', 'CHECKED_OUT', 'CANCELLED')),
    CONSTRAINT [FK_Reservations_Room] FOREIGN KEY ([room_id]) REFERENCES [dbo].[rooms]([room_id])
);
GO

INSERT INTO [dbo].[rooms] ([room_number], [room_type], [price_per_night], [capacity], [floor], [status]) VALUES
('A-101', 'SINGLE', 500000.00, 1, 1, 'AVAILABLE'),
('A-102', 'DOUBLE', 850000.00, 2, 1, 'AVAILABLE'),
('B-201', 'SUITE', 1500000.00, 4, 2, 'OCCUPIED'),
('C-301', 'DELUXE', 2000000.00, 3, 3, 'MAINTENANCE');
GO
