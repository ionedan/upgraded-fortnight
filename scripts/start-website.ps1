# SET JAVA 11 env
Write-Output "Setting up java 11 environment.." 
. $env:USERPROFILE\scripts\setenv-java11.ps1
Write-Output "Done."

#$activeProfile="dev"

Write-Output "Starting uFornight library service.."
& $env:JAVA_HOME/bin/java.exe -jar D:\work\prj\UpgradedFortnight\src\ufortnight\website\target\ufortnightwebsite-0.0.1-SNAPSHOT.jar 
#-Dspring.profiles.active=dev