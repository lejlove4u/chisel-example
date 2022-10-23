
SBT_TOOL=sbt

hdl:
	$(SBT_TOOL) run

test:
	$(SBT_TOOL) test

SimpleFsmTest:
	$(SBT_TOOL) "testOnly SimpleFsmTest"

SimpleFsmSyncTest:
	$(SBT_TOOL) "testOnly SimpleFsmSyncTest"

RisingMealyFsmTest:
	$(SBT_TOOL) "testOnly RisingMealyFsmTest"

RisingMooreFsmTest:
	$(SBT_TOOL) "testOnly RisingMooreFsmTest"

clean:
	$(SBT_TOOL) clean

distclean:
	rm -rf .bsp .idea .DS_Store generated project target test_run_dir
