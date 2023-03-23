package com.ecn.birthday.ui.list.placeholder;

import com.ecn.birthday.Utils;
import com.ecn.birthday.entity.Person;
import lombok.Data;

import java.time.Period;
import java.util.*;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PlaceholderItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<>();

    public static void updateItems(List<Person> persons) {
        ITEMS.clear();
        ITEM_MAP.clear();
        for (Person person : persons) {
            addItem(createPlaceholderItem(person));
        }
    }

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static PlaceholderItem createPlaceholderItem(Person person) {
        return new PlaceholderItem(String.valueOf(person.getId()), person, makeDetails(person));
    }

    private static PersonGeneratedDetail makeDetails(Person person) {
        return new PersonGeneratedDetail(person);
    }


    @Data
    public static class Age {
        public final int years;
        public final int months;
        public final int days;
    }

    @Data
    public static class PersonGeneratedDetail {
        public final Age age;

        public PersonGeneratedDetail(Person person) {
            Date birthday = Utils.parseDatabaseDate(person.getBirthday());
            if (birthday == null) {
                this.age = new Age(0, 0, 0);
                return;
            }
            Date today = new Date();
            // calculate period between birthday and today
            Period period = Period.between(Utils.toLocalDate(birthday), Utils.toLocalDate(today));
            this.age = new Age(period.getYears(), period.getMonths(), period.getDays());
        }
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        public final String id;
        public final Person person;
        public final PersonGeneratedDetail details;

        public PlaceholderItem(String id, Person person, PersonGeneratedDetail details) {
            this.id = id;
            this.person = person;
            this.details = details;
        }

        public String getContent() {
            return "First name: " + person.getFirstName() + ", Last name: " + person.getLastName() + ", Birthday: " + person.getBirthday()
                    + ", Age: " + details.getAge().getYears() + " years, " + details.getAge().getMonths() + " months, " + details.getAge().getDays() + " days";
        }
    }
}