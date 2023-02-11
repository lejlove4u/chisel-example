
SBT_TOOL=sbt

hdl:
	$(SBT_TOOL) run

NestedCompTest:
	$(SBT_TOOL) "testOnly NestedCompTest"

ALUTest:
	$(SBT_TOOL) "testOnly ALUTest"

DelayOneClkTest:
	$(SBT_TOOL) "testOnly DelayOneClkTest"

clean:
	$(SBT_TOOL) clean

distclean:
	./mac_cleanup_objects.sh

win_distclean:
	win_cleanup_objects.bat

#	rm -rf .bsp .idea .DS_Store generated project target test_run_dir
