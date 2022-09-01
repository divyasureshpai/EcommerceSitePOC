package com.testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/UIFeatures", "src/test/resources/APIFeatures" }, glue = {
		"stepdefinitionsUI", "stepDefinitionsAPI",
		"AppHooks" }, tags = "@uitest or @apitest", plugin = { "pretty", "html:test-output/report.html" })
public class TestRunner {

}
