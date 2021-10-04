# Bundle-API
The rapid API for Bundle powered by Ktor and kotlinx.coroutines.

## Configuration
Configuration is managed by environment variables, shown below:
1. `PORT` - The port the server should run on, default 8080
2. `GITHUB_AUTH` - The authentication for github api requests, in the format `username:token` (optional, but increases ratelimit drastically)