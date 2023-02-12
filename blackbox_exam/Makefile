
SBT_TOOL=sbt

hdl:
	$(SBT_TOOL) run

# test:
# 	$(SBT_TOOL) test
# 
# MainTest:
# 	$(SBT_TOOL) "testOnly MainTest"
# 
# MainTestExpect:
# 	$(SBT_TOOL) "testOnly MainTestExpect"
# 
# MainTestWaveform:
# 	$(SBT_TOOL) "testOnly MainTestWaveform"

clean:
	$(SBT_TOOL) clean

distclean:
	./mac_cleanup_objects.sh

win_distclean:
	win_cleanup_objects.bat

#	rm -rf .bsp .idea .DS_Store generated project target test_run_dir
