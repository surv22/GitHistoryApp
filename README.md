# MyGitHistory Application
This application accepts an OAuth Authentication using GitHub Account and returns the user the 5 most widely used
Keywords and the most frequent time of commit.

## End points : 
- GET http://localhost:8080/  -> asks the user to authenticate using GitHub login. If successful it redirects to the same page
                                displaying a welcome message for the github user
                               
- GET http://localhost:8080/Keywords -> returns 5 most used keywords in commit message for that user.
                                      (NOTE: only last 30 commits would be evaluated)
                                      
- GET http://localhost:8080/BusiestCommitHour -> returns the most frequent hour of the last 30 commits in 12 hour clock format

###### NOTE: 
- Replace http://localhost:8080 by any base URL on which service is running

- First base_url/ should be hit to allow OAuth authentication . then only proceed with hitting keywords and BusiestCommitHour endpoints

## ENVRIONMENT VARIABLES : 

- CLIENT_ID  => these tokens are provided with the OAuth app registered with GitHub (b4ffeecf455f311caeb2)

- CLIENT_SECRET  => these tokens are provided with the OAuth app registered with Github (c37d67f5f84ea4195aa3b68e6c5b27abf2958644)

## Unit Tests : 

Unit tests are added but due to some scala version compatibility issue(on my intellij and the dependencies in my pom.xml),
getting "scala.predef$.wraprefarray( ljava/lang/object )lscala/collection/mutable/wrappedarray" error
in Liu of time I have commented the tests for now.. but have tried to include the basic scenarios

## Assumptions made : 

- only considering alphabetic keywords to avoid gibberish terms in the result

- ignoring case matching to return unique words. ie "All" and "all" and "aLl" would be considered same.

- return at max 5 unique most used keywords i.e if only 3 or 4 unique keywords are there, they would be returned as is (i,e can be as min as 1)

