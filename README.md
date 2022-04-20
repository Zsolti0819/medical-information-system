# medical-information-system
Medical information system GUI in Java.

TO DO:

| No. 	 | Issue                                                                                                         	             | Fixed by 	     |
|-------|-----------------------------------------------------------------------------------------------------------------------------|----------------|
| 1   	 | Some appointments cannot be updated for unknown reason.                                                       	             | 	Szaki         |
| 2   	 | At patient creation screen, fields and input data should be controlled (validation)                                       	 | 	Simon         |
| 3   	 | Add search functionality to frontend                                                                                     	  | Andrej	        |
| 4   	 | loggedInUser object needs to be created to identify which user is currently logged in. (Dashboard.java)       	             | Szaki          |
| 5   	 | At appointment creation, created_by is set to 1. We need the loggedInUser to know who created the appointment 	             | Szaki, Zsolti	 |
| 6   	 | Distinguish user based on credentials (admin, doctor, nurse, receptionist)                                    	             | Szaki	         |
| 7   	 | Filling data on edit patient and record screens                                                               	             | Zsolti	        |
| 8   	 | All tables show deleted (=true) items                                                         	                             | Szaki	         |
| 9   	 | Implement delete patient function                                                                             	             | Zsolti         |
| 10  	 | Implement delete record function                                                                              	             | Zsolti         |
| 11  	 | Implement update record function                                                                              	             | Zsolti         |
| 12  	 | Add all blood groups to  blood group enum                                                             	                     | Simon	         |
| 13  	 | Alert messages should be added when updating/deleting/creating is un/successful                                             | Martin         |
| 14  	 | Printing out how many rows was affected on update/delete                                                                    | Szaki          |
| 15 	  | Password hashing                                                                                                            | Szaki          |
| 16 	  | Prescription.java get method by id, and all not deleted                                                                     | Szaki          |
| 17 	  | Appointment update still doesn't work. Index 1 out of bounds for length 1 (AppointmentEdit.java:40)                         | Szaki          |
| 18 	  | At appointments and at prescriptions, doctor names should be displayed instead of doctor ID-s.                              |                |
| 19 	  | Fix search to not show deleted patients                                                                                     | Szaki          |
| 20 	  | At patients screen table, next visit should be displayed                                                                    |                |
| 21 	  | At records and appointments, the date and time should be formatted                                                          |                |