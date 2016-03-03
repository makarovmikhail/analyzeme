package com.analyzeme.controllers;

import com.analyzeme.analyze.AnalyzeFunction;
import com.analyzeme.analyze.AnalyzeFunctionFactory;
import com.analyzeme.repository.FileRepository;
import com.analyzeme.streamreader.StreamToString;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Servlet what return minimum from data using fileName by header called "minimum"
 * Created by Sergey on 05.02.2016.
 */

@WebServlet("/GlobalMinServlet")
public class GlobalMinServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Analyze Factory
		AnalyzeFunctionFactory ServletFactory = new AnalyzeFunctionFactory();
		//Create GlobalMinimum function
		AnalyzeFunction GlobalMinimum = ServletFactory.getFunction("GlobalMinimum");
		double minimum;

		String fileName = request.getParameter("fileName");

		ByteArrayInputStream file = FileRepository.repo.getFileByID(fileName);

         /*
		Convert ByteArrayInputStream into String
         */
		String Data = StreamToString.ConvertStream(file);

		//DataArray = new Point[];
		//minimum=DataArray[GlobalMinimum.Calc(DataArray)].y;
		minimum = -4;
		response.setHeader("minimum", String.valueOf(minimum));

	}
}