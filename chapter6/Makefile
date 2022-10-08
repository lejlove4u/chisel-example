
SBT_TOOL=sbt

hdl:
	$(SBT_TOOL) run

test:
	$(SBT_TOOL) test

TickCounterTest:
	$(SBT_TOOL) "testOnly TickCounterTest"

SlowerCounterTest:
	$(SBT_TOOL) "testOnly SlowerCounterTest"

NerdCounterTest:
	$(SBT_TOOL) "testOnly NerdCounterTest"

TimerTest:
	$(SBT_TOOL) "testOnly TimerTest"

TimerOtherTest:
	$(SBT_TOOL) "testOnly TimerOtherTest"

clean:
	$(SBT_TOOL) clean

distclean:
	rm -rf .bsp .idea .DS_Store generated project target test_run_dir
