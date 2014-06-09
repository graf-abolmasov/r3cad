cucumber {
    features = ['test/functional/cucumber/features']
//    sources = ['test/functional/cucumber']
//    glue = ['classpath:steps', 'classpath:support']
    glue = ['test/functional/cucumber/support', 'test/functional/cucumber/steps']
    formats = ["html:target/test-reports/cucumber/html", "json:target/test-reports/cucumber/report.json"]
}
