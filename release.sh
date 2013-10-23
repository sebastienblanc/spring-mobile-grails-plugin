rm -rf target/release
mkdir target/release
cd target/release
git clone git@github.com:burtbeckwith/grails-spring-mobile.git
cd grails-spring-mobile
grails clean
grails compile

#grails publish-plugin --snapshot --stacktrace
grails publish-plugin --stacktrace
