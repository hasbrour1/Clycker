# Clycker
android app

This android app acts as a clicker for any classroom.  Teachers have the ability to ask questions to the classroom, and they can answer it real time by pressing the answer they think is correct on their phone.  Teachers add questions to the website and throughout their lecture they can ask their students to answer the specified question.  This allows the teacher to know how many students are following along.  It gives each students answer, and can give an average of the amount of students that correctly answer each question.  This will allow teachers to better understand the abilities of their students, and if they need to spend more or less time on subjects.      

The app gets the tests buy using BufferedReader to open a url stream in the TestActivity Class.  The url points to a text file on the server which holds all of the tests.  The app then selects the test based on the test number inputted and uses that test.   

The students answers are stored in a string.  The results string is then loaded onto the Clycker-Web website.  Communication with the web server to load the results is done using java.net.Socket.  The app sends the test results trough the socket in the SendTestData.java class.  

