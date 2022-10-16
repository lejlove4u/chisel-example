
SBT_TOOL=sbt

hdl:
	$(SBT_TOOL) run

test:
	$(SBT_TOOL) test

DebounceTest:
	$(SBT_TOOL) "testOnly DebounceTest"

SyncResetTest:
	$(SBT_TOOL) "testOnly SyncResetTest"

clean:
	$(SBT_TOOL) clean

distclean:
	rm -rf .bsp .idea .DS_Store generated project target test_run_dir
