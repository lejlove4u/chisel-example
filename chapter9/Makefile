
SBT_TOOL=sbt

hdl:
	$(SBT_TOOL) run

test:
	$(SBT_TOOL) test

FlasherTest:
	$(SBT_TOOL) "testOnly FlasherTest"

clean:
	$(SBT_TOOL) clean

distclean:
	rm -rf .bsp .idea .DS_Store generated project target test_run_dir
