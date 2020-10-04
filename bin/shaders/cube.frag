#version 330 core

in float yCol;
out vec4 fragCol;

void main()
{
	fragCol = vec4(0.1, 0.6, yCol+0.4, 1.0);
}
