package com.example.java13_goodspiritswithfx.universe;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Universe {
    public List<LevelOfUniverse> listLevels;

    public Universe(String fname) {
        LevelOfUniverse zero = new LevelOfUniverse(0, 1);
        LevelOfUniverse prev = zero;

        Stream<String> stream = fname.lines();
        ArrayList<String> text = (ArrayList<String>) stream.collect(Collectors.toList());

        int index = 0;
        int kLevels = Integer.parseInt(text.get(index++));
        listLevels = new ArrayList<>(kLevels + 1);
        listLevels.add(zero);
        for (int i = 0; i < kLevels; i++) {
            int kPlanets = Integer.parseInt(text.get(index++));
            LevelOfUniverse level = new LevelOfUniverse(i + 1, kPlanets);
            listLevels.add(level);
            for (int j = 1; j <= kPlanets; j++) {
                String ss = text.get(index++);
                level.setTonnels(j - 1, ss, prev);
            }
            index++;
            prev = level;
        }
    }

    public SpacePath findBestPath() {
        ArrayList<SpacePath> spacePaths = generatePathList();
        SpacePath theBestPath = spacePaths.stream()
                .min(Comparator.comparing(SpacePath::getTotalCost))
                .get();
        Collections.reverse(theBestPath.steps);
        return theBestPath;
    }

    public ArrayList<SpacePath> generatePathList() {
        ArrayList<SpacePath> pathList = new ArrayList<>();
        SpacePath path = new SpacePath();
        Planet[] paradiseWorlds = this.listLevels.get(listLevels.size() - 1).getPlanets();
        for (Planet p : paradiseWorlds)
            findPath(p, this.listLevels.get(0).getPlanets()[0], path, pathList);

        return pathList;
    }

    public void findPath(Planet current, Planet finish, SpacePath previousPath, ArrayList<SpacePath> pathList) {
        SpacePath currentPath;
        if (current.equals(finish)) {
            pathList.add(previousPath);
        } else {
            for (Tonnel t : current.tonnels) {
                currentPath = new SpacePath(previousPath);
                currentPath.steps.add(t);
                findPath(t.from, finish, currentPath, pathList);
            }
        }
    }

    public ArrayList<Tonnel> getTonnelList(ArrayList<Tonnel> tonnels) {
        for (LevelOfUniverse levelOfUniverse : listLevels) {
            for (Planet planet : levelOfUniverse.getPlanets()) {
                for (Tonnel tonnel : planet.tonnels) {
                    tonnels.add(tonnel);
                }
            }
        }
        return tonnels;
    }
}
