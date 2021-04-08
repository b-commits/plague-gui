package models;

public class Country {
    private int x;
    private int y;
    private String name;
    private int population;
    private int infected;
    private boolean virus;
    private boolean lockdown;

    public Country (int x, int y, String name, int population) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.population = population;
        this.infected = 0;
        this.virus = false;
        this.lockdown = false;
    }

    public int getInfected() {
        return infected;
    }

    public void setInfected(int infected) {
        this.infected = infected;
    }

    public String getName() {
        return name;
    }

    public boolean hasVirus() {
        return virus;
    }

    public void setVirus(boolean virus) {
        this.virus = virus;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLockdown(boolean lockdown) {
        this.lockdown = lockdown;
    }

    public boolean isLockdown() {
        return lockdown;
    }
}
