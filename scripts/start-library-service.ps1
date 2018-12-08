# SET JAVA 11 env
Write-Output "Setting up java 11 environment.." 
. $env:USERPROFILE\scripts\setenv-java11.ps1
Write-Output "Done."

$target_path = "D:\work\prj\UpgradedFortnight\src\ufortnight\library-service\target"
#$activeProfile="dev"

Write-Output "Starting uFornight library service.."
& $env:JAVA_HOME/bin/java.exe -jar $target_path\ufortnight.library-service-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=dev