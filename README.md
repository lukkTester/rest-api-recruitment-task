# rest-api-recruitment-task
Few years ago I got a recruitment task from one company. It consisted of 3 scenarios to automate using RestAssured library. Here is my solution.

The content of the task:

API Automation Test

Automate following scenarios using https://carbon-intensity.github.io/api-definitions/#get-regional-regionid-regionid

Scenario 1:
1.	Get carbon intensity for each region
2.	Get intensity value forecast
3.	Sort regions for highest to lowest intensity
4.	Print sorted list in the logs starting with value followed by short name of the region

Scenario 2:
1.	For each region get carbon intensity
2.	Assert that generation mix sums to 100

Scenario 3 (optional):
1.	For each region get carbon intensity
2.	For each fuel type list five regions where the generation percentage is the highest 

Notes:
-	Preferred framework REST Assured
-	Code quality matters
