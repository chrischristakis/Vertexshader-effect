package com.chrischristakis.postprocessing;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.chrischristakis.postprocessing.utils.BufferUtils;

public class Scene 
{
	
	private int vao, vbo;
	private ShaderProgram cubeShader;
	
	private Matrix4f projection, model;
	
	public Scene()
	{
		test();
		cubeShader = new ShaderProgram("cube.vert", "cube.frag");
		
		projection = new Matrix4f().perspective(45.0f, (float)Main.width/(float)Main.height, 0.1f, 10.0f);
		model = new Matrix4f();
	}
	
	int noOfCells = 20;
	float vertArr[];
	public void test()
	{
		ArrayList<Float> verts = new ArrayList<Float>();
		for(int y = 0; y < noOfCells; y++)
			for(int x = 0; x < noOfCells+1; x++)
			{
				if((y+1)%2 != 0)
				{
					verts.add((0.0f+x)/noOfCells);verts.add(0.0f);verts.add((0.0f+y)/noOfCells);
					verts.add((0.0f+x)/noOfCells);verts.add(0.0f);verts.add((1.0f+y)/noOfCells);
				}
				else
				{
					verts.add((0.0f+(noOfCells-x))/noOfCells);verts.add(0.0f);verts.add((0.0f+y)/noOfCells);
					verts.add((0.0f+(noOfCells-x))/noOfCells);verts.add(0.0f);verts.add((1.0f+y)/noOfCells);
				}
			}
		
		vertArr = new float[verts.size()];
		for(int i = 0; i < verts.size(); i++)
			vertArr[i] = verts.get(i);
		
		vao = glGenVertexArrays();
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertArr), GL_STATIC_DRAW);
		glBindVertexArray(vao);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);
	}
	
	public void render()
	{
		cubeShader.bind();
		cubeShader.setMat4f("mvp", new Matrix4f().mul(projection).mul(model));
		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, vertArr.length/3);
	}
	
	public void update()
	{
		model.identity();
		model.translate(new Vector3f(0.0f, 0.0f, -2.2f)).rotateX(0.52f).translate(new Vector3f(-0.5f, 0.0f, -0.5f));
		cubeShader.bind();
		cubeShader.setFloat("timer", (float)GLFW.glfwGetTime());
	}

}
