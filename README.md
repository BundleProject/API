# Bundle-API
The rapid API for Bundle powered by Ktor and kotlinx.coroutines.

## Configuration
Configuration is managed by environment variables, shown below:
1. `PORT` - The port the server should run on, default 8080
2. `GITHUB_AUTH_USER` - The login for github api requests (optional, but increases ratelimit drastically)
3. `GITHUB_AUTH_PASS` - The password for github api requests

(you can use a client id/client secret pair, or a username/personal access token pair for github auth) 
