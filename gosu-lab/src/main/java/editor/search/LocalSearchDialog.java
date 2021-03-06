package editor.search;

import editor.EditorHost;
import editor.FileTree;
import editor.LabFrame;

import java.util.List;

/**
 */
public class LocalSearchDialog extends AbstractSearchDialog
{
  public static State STATE = null;

  public LocalSearchDialog( FileTree searchFile, boolean bReplace )
  {
    super( searchFile, bReplace, bReplace ? "Replace..." : "Find..." );
    setLocal();
    setSize( 400, isRegex() ? 280 : 270 );
  }

  protected void find()
  {
    if( isReplace() )
    {
      super.find();
    }
    else
    {
      setState( new State().save( this ) );
      close();
    }

    highlight();
  }

  private void highlight()
  {
    EditorHost editor = LabFrame.instance().getGosuPanel().getCurrentEditor();
    if( editor.getEditor().getSelectedText() != null )
    {
      editor.getEditor().setCaretPosition( editor.getEditor().getSelectionStart() - 1 );
    }

    editor.removeAllHighlights();
    List<SearchLocation> locations = new TextSearcher( getPattern(), isCaseSensitive(), isWholeWords(), isRegex() ).searchLocal();
    if( locations.size() > 0 )
    {
      editor.highlightLocations( locations );
      editor.gotoNextUsageHighlight();
    }
  }

  @Override
  protected State getState()
  {
    return STATE;
  }
  @Override
  protected void setState( State state )
  {
    STATE = state;
  }
}
