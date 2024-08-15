INSERT INTO files (file_data, title, description, creation_date)
VALUES (lo_from_bytea(0, '\x54686973206973207468652066696c65206465736372697074696f6e'), 'Project Proposal',
        'Detailed project proposal for the upcoming client meeting.', NOW());

INSERT INTO files (file_data, title, description, creation_date)
VALUES (lo_from_bytea(0, '\x54686973206973207468652066696c65206465736372697074696f6e'), 'Financial Report Q2',
        'Quarterly financial report for Q2, including profit and loss statements.', NOW());

INSERT INTO files (file_data, title, description, creation_date)
VALUES (lo_from_bytea(0, '\x54686973206973207468652066696c65206465736372697074696f6e'), 'Employee Handbook',
        'The latest version of the employee handbook, updated for 2024.', NOW());

INSERT INTO files (file_data, title, description, creation_date)
VALUES (lo_from_bytea(0, '\x54686973206973207468652066696c65206465736372697074696f6e'), 'Marketing Strategy',
        'Comprehensive marketing strategy plan for the new product launch.', NOW());

INSERT INTO files (file_data, title, description, creation_date)
VALUES (lo_from_bytea(0, '\x54686973206973207468652066696c65206465736372697074696f6e'), 'Client Contract',
        'Signed contract with the client, including all terms and conditions.', NOW());
