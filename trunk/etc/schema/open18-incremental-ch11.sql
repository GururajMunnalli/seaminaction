ALTER TABLE PUBLIC.FACILITY ADD COLUMN AGENT_ID BIGINT NULL;
ALTER TABLE PUBLIC.FACILITY ADD CONSTRAINT PUBLIC.FK_FACILITY_REF_MEMBER FOREIGN KEY(AGENT_ID) REFERENCES PUBLIC.MEMBER(ID);
