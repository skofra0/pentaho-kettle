# cleanup unused jars
# find . -name '*.jar' -delete

ant clean-all
ant resolve
ant dist
