# Hogwarts Potions

## Story

We've been helping the wizards at Hogwarts in the first sprint (_50 points to Codecool!_).
It is time to spare parchments and digitize all the paperwork about persisting data by building a database.
Also, the Student on Potions class could use some help, so let's see if we can lend a hand!

## What are you going to learn?

You will learn and practice how to do the following things in Java Spring:

- Spring Data
- Spring Data Queries
- Spring Database Connection
- Spring JPA Repositories



## Tasks

1. The school seems to have run out of parchments! Create a database to persist Students and Rooms.
    - The `Student` class is an `Entity`
    - There is a `JpaRepository` interface dedicated to `Student` entities, which can persist `Student` objects by their `Long` ids.
    - The `Room` class is an `Entity`
    - There is a `JpaRepository` interface dedicated to `Room` entities, which can persist `Room` objects by their `Long` ids.

2. All services should use the database. This also includes handling requests. Every piece of data used by responses should originate from the database.
    - Endpoints for creating, finding, deleting, updating, finding available rooms, finding rooms for rat owners should create the same response, as the in-memory database did before.

3. The basics of the Potions class are Recipes. By Recipes, we can identify Potions later. A `Recipe` has an `id`, `name`, a `Student` who brew it, and -of course- a list of `Ingredients`. An `Ingredient` contains an `id` and a `name`.
    - The `Ingredient` class is an `Entity`.
    - There is a `JpaRepository` interface dedicated for the `Ingredient` entities, which can persist `Ingredient` objects by their `Long` ids.
    - The `Recipe` class is an `Entity`.
    - There is a `JpaRepository` interface dedicated for the `Recipe` entities, which can persist `Recipe` objects by their `Long` ids.

4. A `Potion` has an `id`, `name`, a `Student` who brews it, a list of `ingredients`, a `BrewingStatus` and a `Recipe`. Until a potion doesn't have 5 ingredients, its `BrewingStatus` is `brew`. After that, if there was already a `Recipe` with the same ingredients (in any order), the status should be `replica`. Otherwise the status should be `discovery`. Create an endpoint at `/potions`. Here, you should list all `potions`.
    - Potions are persisted in the database.
    - At `/potions` all existing `potions` are listed.

5. If you aim to know _" how to bottle fame, brew glory.."_ and the other things Professor Snape told you at the beginning of the class, you should take notes of your potions. Your task is to learn the ins and outs of potion brewing by handling `POST` requests at `/potions`. The request would consist of a `Potion` just brewed, containing the `Student's id`, and the `list of Ingredients`. If a `Recipe` with these `Ingredients` (__in any order__) existed before, we can say, that the `Potion` is a `replica`. If no such `Recipe` existed, then it's a `discovery`, so the `Recipe` should also be persisted.
    - There is an endpoint at `/potions`, where a `student` can brew the `Potion` by sending a `POST request`.
    - The list of `Ingredients` is checked if it matches any `Potion`.
    - If the brew is proved to be a `discovery`, the `Recipe` is persisted with the `list of Ingredients`, the `Student`, and with a name generated from the `Student's name` _(e.g. "John Doe's discovery
    - The response contains the persisted `Potion`.

6. By sending a `GET request` to `/potions/{student-id}`, list all explored `Potions` of a `Student`.
    - At `/potions/{student-id}` all known `Potions` of a given `Student` are listed.

7. Let's get our cauldrons dirty and start brewing by trial and error. In the beginning, a `POST request` is sent to `/potions/brew`, containing the Student's id. At this point, the soon-to-be `Potion` should be persisted with the `Student` and the `Status`. The response should contain the `Potion`'s properties so far, including its `id`. After this, brewing can happen by sending a `PUT request` at `/potions/{potion-id}/add`, containing __one__ ingredient at a time. Every added `Ingredient` should be checked if known before, or be persisted. The response should contain the `Potion` object, including the so far added `Ingredients`. After adding an `Ingredient` one should be able to get help by sending a `GET request` to `/potions/{potion-id}/help`. This endpoint should return all possible `Recipes` that the `Potion` with the current `Ingredients` can be. Once the `Potion` contains 5 `Ingredients`, you should check whether it is a `replica` or a `discovery`.
    - By sending a `POST request` to `/potions/brew` a new `Potion` is generated containing the `Student` and the `status of brewing`.
    - By sending a `PUT request` to `/potions/{potion-id}/add` the `Potion` with the potion-id gets updated the new `Ingredient`. The response contains the updated `Potion` object.
    - If the potion has less than 5 `Ingredients`, if recieving a `GET request` to `/potions/{potion-id}/help`, the application should return in response with the possible `Recipes` containing the same `Ingredients` as the `Potion` brewing.

8. [OPTIONAL] Create a form for brewing potions by solo ingredients. The form should have an input for the Student's id, some kind of input field or dropdown menu for a single ingredient's name, and a submit button to add ingredients one by one. When adding the ingredients, there should be a help button. After hitting the help button, at least 5 and at most 10 recipes should appear, which contains the same ingredients. When the potion does not match any known recipe, have a field to name it properly. You can use any templating engine or frontend framework/library.
    - There is a page containing a `<form>` for potion brewing, with an input field for the ingredients and a submit button.
    - When the help button is hit, at least 5 recipes appear, containing the same ingredients that were put so far in the potion.
    - If the status of the potion is discovery, a field appears to give a special name for the potion.
    - After submitting the form, the potion is persisted.

9. [OPTIONAL] If the newly brewed potion proves to be a discovery, after showing the completed potion, the frontend should have a success message to celebrate the discovery. You should create at least 3 greeting messages and randomly pick one from them by each discovery. If the potion seems to be a replica, the user should see data about how many times they have brewed it.
    - Whenever a new potion gets discovered, a success message appears.
    - Whenever a new potion gets discovered, there is at least 3 kind of success messages to appear.
    - Whenever a new potion gets replicated, data should appear about how many times it has been brewed by the Student.

## General requirements

None

## Hints



## Background materials

- [Spring Database Connection](project/curriculum/materials/competencies/java-spring-data/sping-database-connection.md.html)

- [Spring Data](project/curriculum/materials/competencies/java-spring-data/spring-data.md.html)

- [Spring Data Queries](project/curriculum/materials/competencies/java-spring-data/spring-data-queries.md.html)

- [Spring JPA Repositories](project/curriculum/materials/competencies/java-spring-data/spring-jpa-repositories.md.html)

- [JPA Entity Annotations](project/curriculum/materials/competencies/java-persistence/jpa-entity-annotations.md.html)

- [JPA Entity Relations](project/curriculum/materials/competencies/java-persistence/jpa-entity-relations-one-to-one.md.html)

- [JPA Entity Annotations](project/curriculum/materials/competencies/java-persistence/jpa-entity-relations-one-to-many.md.html)

- [JPA Entity Annotations](project/curriculum/materials/competencies/java-persistence/jpa-entity-relations-many-to-many.md.html)

- [Spring testing](project/curriculum/materials/competencies/java-spring-basics/spring-testing.md.html)

- <i class="far fa-candy-cane"></i> [The difference between integration and unit tests](https://stackoverflow.com/questions/10752/what-is-the-difference-between-integration-and-unit-tests)
