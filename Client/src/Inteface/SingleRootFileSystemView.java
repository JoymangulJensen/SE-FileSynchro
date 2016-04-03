/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Inteface;

import java.io.File;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Selwyn
 */
public class SingleRootFileSystemView extends FileSystemView
{
	File root;
	File[] roots = new File[1];

	public SingleRootFileSystemView(File root)
	{
		super();
		this.root = root;
		roots[0] = root;
	}

	@Override
	public File createNewFolder(File containingDir)
	{
		File folder = new File(containingDir, "New Folder");
		folder.mkdir();
		return folder;
	}

	@Override
	public File getDefaultDirectory()
	{
		return root;
	}

	@Override
	public File getHomeDirectory()
	{
		return root;
	}

	@Override
	public File[] getRoots()
	{
		return roots;
	}
}