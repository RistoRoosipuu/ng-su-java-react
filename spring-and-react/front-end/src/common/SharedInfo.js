const APILocation = 'http://localhost:8080';


const API = {
    uploadUrl: `${APILocation}/uploadFile`,
    dateUrl: `${APILocation}/hello`,
    hospitalUrl: `${APILocation}/hospital`,
    generalInfoUrl: `${APILocation}/generalInfo`,
    postSymptomsUrl: `${APILocation}/possibleDiseases`,
    startInteractionUrl: `${APILocation}/startGame`,
    agreeUrl: `${APILocation}/agree`,
    denyUrl: `${APILocation}/deny`,
};

export default API;