git filter-branch -f --env-filter "
    GIT_AUTHOR_NAME='Cory Forward'
    GIT_AUTHOR_EMAIL='z41r951@montana.edu'
    GIT_COMMITTER_NAME='Cory Forward'
    GIT_COMMITTER_EMAIL='z41r951@montana.edu'
  " HEAD
