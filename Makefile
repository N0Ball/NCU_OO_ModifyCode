# Clear Before and Build

final: build
	cd bin; java bgWork/Launcher

build: clean

	find ./ -name '*.java' | xargs javac -d bin

clean:

	find ./ -name "*.class" -type f | xargs rm -rf