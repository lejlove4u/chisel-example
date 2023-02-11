
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

PwmTest:
	$(SBT_TOOL) "testOnly PwmTest"

Pwm1Test:
	$(SBT_TOOL) "testOnly Pwm1Test"

MemoryTest:
	$(SBT_TOOL) "testOnly MemoryTest"

clean:
	$(SBT_TOOL) clean

distclean:
	./mac_cleanup_objects.sh

win_distclean:
	win_cleanup_objects.bat

#	rm -rf .bsp .idea .DS_Store generated project target test_run_dir
