
SBT_TOOL=sbt

hdl:
	$(SBT_TOOL) run

test:
	$(SBT_TOOL) test

ExampleTest:
	$(SBT_TOOL) "testOnly ExampleTest"

simpleTest:
	$(SBT_TOOL) "testOnly simpleTest"

simpleTestExpect:
	$(SBT_TOOL) "testOnly simpleTestExpect"

waveformCounterTest:
	$(SBT_TOOL) "testOnly waveformCounterTest"

waveformCounterPrinterTest:
	$(SBT_TOOL) "testOnly waveformCounteaPrinterrTest"

clean:
	$(SBT_TOOL) clean

distclean:
	rm -rf .bsp .idea .DS_Store generated project target test_run_dir
