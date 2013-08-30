java_import 'org.andrewzures.middleware.TTTFactory'
require 'factory'

class JFactory < Factory
  include TTTFactory

  def getBoard(type)
    get_board(type)
  end

  def getPlayer(type, mark)
    get_player(type, mark)
  end
end