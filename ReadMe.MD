# Diet Planner

Diet Planner

## Description

Suggests a list of recipes to follow given your preferences

## Getting Started

### Dependencies

* At least java 8 version 
* Maven

### Building

* run `mvn package`
* A jar executable should be generated at `target/diet-planner.jar`

### Executing program

* run `java -jar target/diet-planner.jar`

to test access http://localhost:8080/swagger-ui/
 
 
alternatively you can run the following with your preferences

```$xslt
curl -X POST "http://localhost:8080/diet" \
-H "accept: */*" \
-H "Content-Type: application/json" \
-d "{ \"dailyCaloricNeed\": 3000, \
\"diet\": \"GENERAL_HEALTHFUL\", \
\"householdSize\": 4, \
\"numBreakfast\": 1, \
\"numDays\": 3, \
\"numDinner\": 1, \
\"numLunch\": 1}"
```

### Unit Testing 

* run `mvn test`

## Author

Marco Martinez
[@MarcoStaccato](https://github.com/MarcoStaccato)