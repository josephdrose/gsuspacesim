package spacesim;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;

public class ExpertSystem {
	private KnowledgeBase kbase;
	private StatelessKnowledgeSession ksession;
	
	public ExpertSystem(String filename) throws Exception{
		KnowledgeBuilder kbuilder=KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource(filename), ResourceType.DRL);
		KnowledgeBuilderErrors errors=kbuilder.getErrors();
		if (errors.size()>0) {
			for (KnowledgeBuilderError error:errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		kbase=KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		ksession=kbase.newStatelessKnowledgeSession();
	}
	
	public void go(Object o){
		ksession.execute(o);
	}

	public void end(){
	}
}