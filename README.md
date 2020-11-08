# gift-exchange

## Assumptions

For simplification purposes, we will assume:
* All family members will have unique first name
* Each individual will have zero to two parents
  * Zero parents should happen at the highest level of the family representation to ensure finite bounds
* Each individual will have zero or one spouse
  * If the individual has children, then he/she has one spouse
* The gift recipient assignments are not secret

## Building

### Docker

Assuming that Docker has been installed for your OS.

Please execute the following in the terminal:

```
docker build -t gift-exchange .
```

### Native

Please execute the following in the terminal:

```
./gradlew clean
./gradlew build
```

## Running

### Input

Please create a file that has a list of all your family members.
The contents of this file will be passed into `stdin` for the program.

### Format

Use a separate line for each individual in your family.

The structure should resemble the following:
```
<firstName>,<parent1>,<parent2>,<spouse>
```

### Example

John is the child of Roger and Mary.
Sarah is the child of Eric and Diana.
John is married to Sarah.
John and Sarah have two children: Carl and Patricia.

```
John,Roger,Mary,Sarah
Sarah,Eric,Diana,John
Roger,,,Mary
Mary,,,Roger
Carl,John,Sarah
Patricia,John,Sarah
```

### Output Format

A list of family members with their designated

### Docker

Assumes the `gift-exchange` container image was built.

Please execute the following:

```
cat ./familyMembersList.txt | docker run -it gift-exchange
```

### Native

Assumes that the Java 8 Runtime is installed.

Please execute the following:

```
cat ./familyMembersList.txt | java -jar ./build/libs/giftexchange.jar
```