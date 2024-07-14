package com.teixeirarios.mad.lib.domain.entities.skills;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SkillCatalog {

    static ArrayList<String> activeSkills = new ArrayList<String>();
    static ArrayList<String> availableSkills = new ArrayList<String>();

    public static ArrayList<String> getActiveSkills() {
        return activeSkills;
    }

    public static void addActiveSkill(String skillName) {
        activeSkills.add(skillName);
    }

    public static void clearSkills() {
        activeSkills.clear();
    }

    public static void initSkills() {
        availableSkills.clear();
        availableSkills.add("SoundAttack");
        availableSkills.add("ForceField");
        availableSkills.add("Vampires");
        availableSkills.add("FireWalk");
        availableSkills.add("Lightning");
    }

    public static ArrayList<String> getAvailableSkills() {
        return availableSkills;
    }

    public static ArrayList<String> getRandomSkills() {
        Random rand = new Random();
        ArrayList<String> result = new ArrayList<>();

        List<String> combinedList = mergeLists(availableSkills, activeSkills);

        while (result.size() < 3) {
            int index = rand.nextInt(combinedList.size());
            String skill = combinedList.get(index);
            if (!result.contains(skill)) {
                result.add(skill);
            }
        }

        return result;
    }

    static List<String> mergeLists(ArrayList<String> left, ArrayList<String> right) {
        return Stream.of(left, right)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
