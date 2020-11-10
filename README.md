# gift-exchange

## Assumptions

These assumptions are made for simplification purposes:
* All family members will have unique names
* Each individual will have zero or two parents
  * Zero parents should happen at the highest level of the family representation to ensure finite bounds
* Each individual will have zero or one spouse
  * If the individual has children, then he/she has one spouse

## Building

### Tests

Gradle will run the test stage (called `test`) automatically during the `build` stage.

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

### Examples

#### Example 1

Available as a text file [here](examples/compatibleOnlyWithLooseFilter.txt)

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

#### Example 2

**NOTE: This family structure is solvable when excluding matches with immediate family members**

Available as a text file [here](examples/compatibleWithImmediateFamilyFilter.txt)

Angus is married to Melinda.
Beckett is married to Harriett.
Angus and Melinda have two children: Clifford and Sylvia.
Beckett and Harriett have two children: Nigel and Wilma.
Clifford and Wilma are married.

```
Angus,,,Melinda
Beckett,,,Harriett
Clifford,Angus,Melinda,Wilma
Sylvia,Angus,Melinda
Nigel,Beckett,Harriett
Wilma,Beckett,Harriett,Clifford
```

### Output Format

A list of family members with their designated gift recipients, comma-separated.

Will resemble this:
```
GIFTER,RECIPIENT
<personA>,<personB>
...
<personX>,<personY>
```

### Options

Supply the `--allow-immediate-family` flag to allow pairing between immediate family members.
If this flag is not supplied, by default, pairing will NOT be permitted between immediate family
members.

### Docker

Assumes the `gift-exchange` container image was built.

Please execute the following:

```
cat ./familyMembersList.txt | docker run -i gift-exchange
```

### Native

Assumes that the Java 8 Runtime is installed.

Please execute the following:

```
cat ./familyMembersList.txt | java -jar ./build/libs/giftexchange.jar
```