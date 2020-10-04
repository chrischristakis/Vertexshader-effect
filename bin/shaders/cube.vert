#version 330 core

layout(location = 0) in vec3 aPos;
out float yCol;

uniform mat4 mvp = mat4(1.0);
uniform float timer = 0.0;
#define PI 3.14159265359

void main()
{
	float y = aPos.y+0.09*sin(2*PI*aPos.x+timer*2)+0.09*cos(2*PI*aPos.z+timer*2);
	gl_Position = mvp * vec4(aPos.x, y, aPos.z, 1.0);
	yCol = y;
}
