package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileMode {

	public static File openFileMode(String description, String ... extensions)
	{
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter langFilter = new FileNameExtensionFilter(
		description, extensions);
		fileChooser.setFileFilter(langFilter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		String path = "";
		File file = null;
		try
		{
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				path = fileChooser.getSelectedFile().getAbsolutePath();
				file = new File(path);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return file;
	}

	public static String saveFile(Object content, String description,
	String extension)
	{
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter langFilter = new FileNameExtensionFilter(
		description, extension);
		fileChooser.setFileFilter(langFilter);
		String path = "";
		try
		{
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				path = fileChooser.getSelectedFile().getAbsolutePath();
				if ( ! path.endsWith("." + extension))
				{
					path += "." + extension;
				}
				File file = new File(path);
				if ((file.exists() && JOptionPane.OK_OPTION == JOptionPane
				.showConfirmDialog(null,
				"The file exists, do you want to replace it?", "File Exists",
				JOptionPane.YES_NO_OPTION))
				|| ! file.exists())
				{
					ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(path));
					oos.writeObject(content);
					oos.close();
				}
			}
			return path;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return path;
		}
	}
}