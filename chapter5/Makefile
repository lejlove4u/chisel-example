
SBT_TOOL=sbt

hdl:
	$(SBT_TOOL) run

test:
	$(SBT_TOOL) test

SevenSegmentTest:
	$(SBT_TOOL) "testOnly SevenSegmentTest"

clean:
	$(SBT_TOOL) clean

distclean:
	rm -rf .bsp .idea .DS_Store generated project target test_run_dir
