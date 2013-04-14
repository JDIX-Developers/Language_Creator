package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Jordan Aranda Tejada
 */
public class FileChooser {

	/**
	 * Opens a file with a file chooser
	 * 
	 * @param description - The description of the file
	 * @param extensions - The extension of the file. Could be multiple
	 *            extensions.
	 * @return The file loaded
	 */
	public static File openFile(String description, String ... extensions)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(description,
		extensions));
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

	/**
	 * Saves a file choosen by a file chooser
	 * 
	 * @param content - The object to save
	 * @param description - The description of the file
	 * @param extension - The extension of the file
	 * @param file - The file in where to save
	 * @return The path of the saved file
	 */
	public static String saveObjectFile(Object content, String description,
	String extension, File file)
	{
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter langFilter = new FileNameExtensionFilter(
		description, extension);
		fileChooser.setFileFilter(langFilter);
		fileChooser.setSelectedFile(file);
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
				File file2 = new File(path);
				if ((file2.exists() && JOptionPane.OK_OPTION == JOptionPane
				.showConfirmDialog(null,
				"The file exists, do you want to replace it?", "File Exists",
				JOptionPane.YES_NO_OPTION))
				|| ! file2.exists())
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

	/**
	 * Saves a file choosen by a file chooser
	 * 
	 * @param content - The byte array to save
	 * @param description - The description of the file
	 * @param extension - The extension of the file
	 * @return The path of the saved file
	 */
	public static String saveFile(byte[] content, String description,
	String extension)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(description,
		extension));
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
				.showConfirmDialog(null, Lang.getLine("file_exists_replace"),
				Lang.getLine("file_exists"), JOptionPane.YES_NO_OPTION))
				|| ! file.exists())
				{
					FileOutputStream fos = new FileOutputStream(path);
					fos.write(content);
					fos.close();
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