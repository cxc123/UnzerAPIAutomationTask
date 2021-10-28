# UnzerAPITESTTASK
API Test task for unzer

## Getting Started
1) End to end are under package e2e which run complete end to end flow
2) Component level test under package componenttest which run complete component level test


## Prerequisites
1) Install Java,Maven and Github
2) Generate bearer token using https://m3o.com/account/keys

## Running the tests
1) command to run all test :- mvn clean test
2) Run below command to run test under e2e package :- mvn clean test -U -Pe2etest
3) Run below command to run test under componenttest package :- mvn clean test -U -Pcomponenttest
4) Run below command with base url parameter and bearer token:-  mvn clean test -U -P{profile} -DTEST_BASE_URL=${BASEURL} -DBEARER_TOKEN=${BEARER_TOKEN}


## Reports

refer target/surefire-reports/emailable-report.html for report