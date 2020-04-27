# jersey-backend-template
> Backend base for microservices

Develop branch

## DEPLOYMENT

CI/CD Process Implemented by Github Actions/Heroku Integration
The Action is implemented in .github/workflows/maven.yml

### Flow

1. Creates a docker image
2. Packages using Maven with the maven-docker plugin
3. Pushes this image to the heroku container registry associated with our heroku app using Heroku CLI
4. Releases this image to deploy on our heroku app using Heroku CLI

The app will live here: https://foodapp-user-service.herokuapp.com/

### Notes

Heroku requires 0.0.0.0 and not localhost as server name for some reason
Port needs to be System.getenv("PORT");


