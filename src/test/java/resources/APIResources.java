package resources;

public enum APIResources {
    userPath("/api/v4/projects/");
    private String resource;

    APIResources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
